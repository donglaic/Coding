import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { ProductsComponent } from './products.component';
import { ProductComponent } from './product.component';
import { RatingComponent } from './rating.component';
import { TruncatePipe } from './truncate.pipe';
import { JumboTronComponent } from './jumbotron.component';
import { UserFormComponent } from './user-form.component';
import { LoginComponent } from './login.component';
import { LoginService } from './login.service';
import { HomeComponent } from './home.component';
import { NotFoundComponent } from './notfound.component';
import { routing } from './app.routing';
import { ArtistComponent } from './artist.component';

@NgModule({
  declarations: [
    AppComponent,ProductsComponent,ProductComponent,RatingComponent,
    TruncatePipe,JumboTronComponent,UserFormComponent,LoginComponent,
    HomeComponent,NotFoundComponent,ArtistComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    routing
  ],
  providers: [LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
