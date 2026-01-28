import { Routes } from '@angular/router';
import { LoginComponent } from './component/login/login';
import { Admin } from './component/admin/admin';
import { UserComponent } from './component/user/user';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: Admin }, //
  { path: 'user', component: UserComponent } //
];
