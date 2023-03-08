import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminEditComponent } from './admin-edit/admin-edit.component';
import { AdminComponent } from './admin/admin.component';
import { CandiesComponent } from './candies/candies.component';
import { CandyDetailComponent } from './candy-detail/candy-detail.component';
import { CartComponent } from './cart/cart.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  { path: 'candies', component: CandiesComponent},
  { path: '', redirectTo: '/homepage', pathMatch: 'full' },
  { path: 'detail/:id', component: CandyDetailComponent },
  { path: 'homepage', component: HomepageComponent},
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminComponent},
  { path: 'admin-edit/:id', component: AdminEditComponent},
  { path: 'cart', component: CartComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
