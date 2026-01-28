import { HttpClient } from '@angular/common/http';
import { Component, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CartService } from '../../service/cart.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './admin.html'
})
export class Admin implements OnInit {
  http = inject(HttpClient);
  plist: any[] = [];

  currentUserId: number = 1;

  constructor(private cartService: CartService) {}

  search_id = signal<number>(0);
  pObj: any = {
    id: null,
    product_name: '',
    quantity: ''
  };

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.http.get("http://localhost:8080/api/products").subscribe((res: any) => this.plist = res);
  }

  onAddToCart(productId: number) {
    this.cartService.addToCart(this.currentUserId, productId, 1).subscribe({
      next: (res) => alert("Đã thêm sản phẩm vào giỏ hàng thành công!"),
      error: (err) => alert("Không thể thêm vào giỏ: " + err.message)
    });
  }

  create() {
    this.http.post("http://localhost:8080/api/products", this.pObj).subscribe({
      next: (res) => {
        this.getAll();
        this.resetPObj();
      },
      error: (err) => { alert("Error"); },
    });
  }

  onSearch() {
    this.http.get("http://localhost:8080/api/products/" + this.search_id()).subscribe({
      next: (res: any) => { this.plist = res; },
      error: (error) => { alert("Error" + error); }
    });
  }

  update() {
    this.http.put("http://localhost:8080/api/products/" + this.pObj.id, this.pObj).subscribe({
      next: () => {
        this.getAll();
        this.resetPObj();
      },
      error: (error) => { alert("Error"); }
    });
  }

  onEdit(item: any) {
    this.pObj = { ...item };
  }

  delete(item: any) {
    if (confirm("Xác nhận xóa sản phẩm?")) {
      this.http.delete("http://localhost:8080/api/products/" + item.id).subscribe({
        next: () => { this.getAll(); },
        error: (error) => { alert("Error"); }
      });
    }
  }

  private resetPObj() {
    this.pObj = { id: null, product_name: '', quantity: '' };
  }
}
