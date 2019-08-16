import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';
import { NotFoundComponent } from './notfound.component';
import { SpotifyComponent } from './spotify/spotify.component';
import { ArtistComponent } from './spotify/artist.component';
import { AuthGuard } from './auth-guard.service';
import { LoginComponent } from './login.component';
import { PreventUnsavedChangesGuard } from './prevent-unsaved-changes-guard.service';
import { UserFormComponent } from './users/user-form.component';

export const routing = RouterModule.forRoot([
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'signup', component: UserFormComponent},
    { path: 'login', component: LoginComponent, canDeactivate: [PreventUnsavedChangesGuard]},
    { path: '**', component: NotFoundComponent }
]);