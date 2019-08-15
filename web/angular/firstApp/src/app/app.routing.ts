import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';
import { NotFoundComponent } from './notfound.component';
// import { SpotifyComponent } from './spotify.component';

export const routing = RouterModule.forRoot([
    { path: '', component: HomeComponent },
    // { path: 'spotify', component: SpotifyComponent },
    { path: '**', component: NotFoundComponent }
]);