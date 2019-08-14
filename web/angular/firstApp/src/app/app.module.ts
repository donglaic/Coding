import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RatingComponent } from './rating.component';
import {FormsModule} from '@angular/forms'
import { ProductComponent } from './product.component';
import { ProductsComponent } from './products.component';

@NgModule({
  declarations: [
    AppComponent,RatingComponent,ProductComponent,ProductsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }