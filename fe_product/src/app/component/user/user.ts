import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserResDTO, UserCreDTO } from '../../model/user.model';
import { UserService } from '../../service/user.service';
import { FormsModule } from '@angular/forms';
import { CartService } from '../../service/cart.service';
import { CartRes } from '../../model/cart.model';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './user.html',
})
export class UserComponent implements OnInit {
  users: UserResDTO[] = [];
  currentUser: UserCreDTO = { username: '', password: '' };
  isEdit = false;
  cartData?: CartRes;

  constructor(
    private userService: UserService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

isLoadingCart = false;

viewCart(userId: number) {
  this.isLoadingCart = true;
  this.cartService.getCartByUserId(userId).subscribe({
    next: (data) => {
      this.cartData = data;
      this.isLoadingCart = false;
    },
    error: (err) => {
      this.isLoadingCart = false;
      alert("Giỏ hàng trống!");
    }
  });
}

  loadUsers() {
    this.userService.getAll().subscribe(data => this.users = data);
  }

  saveUser() {
    if (this.isEdit && this.currentUser.id) {
      this.userService.update(this.currentUser.id, this.currentUser).subscribe(() => {
        this.loadUsers();
        this.resetForm();
      });
    } else {
      this.userService.create(this.currentUser).subscribe(() => {
        this.loadUsers();
        this.resetForm();
      });
    }
  }

  editUser(user: UserResDTO) {
    this.isEdit = true;
    this.currentUser = { ...user };
  }
approve(id: number) {
  if (!id) {
    alert("Lỗi: Không tìm thấy ID người dùng!");
    return;
  }

  this.cartService.acceptCart(id).subscribe({
    next: (res) => {
      alert(res);
      this.viewCart(id);
    },
    error: (err) => {
      alert("Lỗi khi duyệt giỏ hàng: " + (err.error || "Không xác định"));
    }
  });
}
  deleteUser(id: number) {
    if (confirm('Bạn có chắc muốn xóa người dùng này? Thao tác này sẽ xóa cả giỏ hàng liên quan.')) {
      this.userService.delete(id).subscribe(() => {
        this.loadUsers();
        if (this.cartData?.user_id === id) this.cartData = undefined;
      });
    }
  }

  resetForm() {
    this.currentUser = { username: '', password: '' };
    this.isEdit = false;
  }

  closeCart() {
    this.cartData = undefined;
  }
}
