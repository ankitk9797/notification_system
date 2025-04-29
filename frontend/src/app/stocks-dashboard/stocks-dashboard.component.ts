import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {forkJoin} from 'rxjs';

interface Stock {
  name: string;
  price: number;
  subscribed: boolean;
}
@Component({
  standalone: true,
  selector: 'app-stocks-dashboard',
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './stocks-dashboard.component.html',
  styleUrl: './stocks-dashboard.component.css'
})
export class StocksDashboardComponent implements OnInit {

  protected stocks: Stock[] = [];
  showModal = false;
  email: string|null = '';
  token: string|null = '';
  protected subscribedStocks: string[] = [];

  newStock: Partial<Stock> = {
    name: '',
    price: 0
  };
  constructor(private http: HttpClient,private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.email = localStorage.getItem('email');
    this.getStockList();
  }

  getStockList(){
    console.log('list');
    this.token = localStorage.getItem('token');

    if (!this.token) {
      console.error('No JWT token available');
      return;
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}` // Attach the JWT token here
    });
    void forkJoin({
      stocks: this.http.get<Stock[]>('http://localhost:9090/api/stocks/list', { headers }),
      subscribedNames: this.http.get<string[]>(`http://localhost:9090/api/users/subscribed-stocks/${this.email}`, { headers })
    }).subscribe(({ stocks, subscribedNames }) => {
      this.subscribedStocks = subscribedNames;
      this.stocks = stocks.map((stock) => {
        if (subscribedNames.includes(stock.name)) {
          stock.subscribed = true;
        } else {
          stock.subscribed = false;
        }
        return stock;
      });
      this.cdr.detectChanges();
    }, error => {
      console.error('Error fetching stock data:', error);
    });
  }

  subscribe(stock: Stock) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}` // Attach the JWT token here
    });
    this.http.post('http://localhost:9090/api/stocks/subscribe-stock', {"stock": stock.name, "email": localStorage.getItem('email')}, { headers })
      .subscribe(() => {
        this.getStockList();
      });
  }

  unsubscribe(stock: Stock) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}` // Attach the JWT token here
    });
    this.http.post('http://localhost:9090/api/stocks/unsubscribe-stock', {"stock": stock.name, "email": localStorage.getItem('email')}, { headers })
      .subscribe(() => {
        this.getStockList();
      });

  }

  openAddStockModal() {
    this.newStock = { name: '', price: 0 };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  submitStock() {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}` // Attach the JWT token here
    });
    if (this.newStock.name && this.newStock.price != null) {
      this.http.post('http://localhost:9090/api/stocks/create', {"name": this.newStock.name, "price": this.newStock.price}, { headers })
        .subscribe(()=>{
          this.http.get<Stock[]>('http://localhost:9090/api/stocks/list', { headers })
            .subscribe(result => {
              this.getStockList();
            });
        });
      this.closeModal();
    }
  }

}
