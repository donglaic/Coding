import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { LoginService } from './login.service';
import { HomeComponent } from './home.component';
import { NotFoundComponent } from './notfound.component';
import { routing } from './app.routing';
import { LoginComponent } from './login.component';
import { AuthGuard } from './auth-guard.service';
import { PreventUnsavedChangesGuard } from './prevent-unsaved-changes-guard.service';
import { SpotifyModule } from './spotify/spotify.module';
import { UserModule } from './users/user.module';
import { spotifyRouting } from './spotify/spotify.routing';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NotFoundComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    SpotifyModule,
    UserModule,
    spotifyRouting,
    routing,
    ReactiveFormsModule
  ], 
  providers: [LoginService,AuthGuard,PreventUnsavedChangesGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
