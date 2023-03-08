import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; 

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CandiesComponent } from './candies/candies.component';
import { CandyDetailComponent } from './candy-detail/candy-detail.component';
import { CandySearchComponent } from './candy-search/candy-search.component';

import { HttpClientModule } from '@angular/common/http';
import { CartComponent } from './cart/cart.component';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { CartCandyDetailComponent } from './cart-candy-detail/cart-candy-detail.component';
import { NavComponent } from './nav/nav.component';
import { AdminEditComponent } from './admin-edit/admin-edit.component';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';

@NgModule({
  declarations: [
    AppComponent,
    CandiesComponent,
    CandyDetailComponent,
    CandySearchComponent,
    CartComponent,
    LoginComponent,
    AdminComponent,
    CartCandyDetailComponent,
    NavComponent,
    AdminEditComponent,
    HeaderComponent,
    HomepageComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
