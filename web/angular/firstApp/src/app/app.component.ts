import { Component } from '@angular/core';
import { SpotifyService } from './spotify.service';
import { FormControl } from '@angular/forms';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';

@Component({
  selector: 'app-root',
  template:``,
  providers:[SpotifyService]
})
export class AppComponent {
  isLoading = false;
  searchControl = new FormControl();
  artists = [];

  constructor(private _spotifyService:SpotifyService){
    this._spotifyService.getSpotifyData().subscribe(data=>console.log(data));
  }

}