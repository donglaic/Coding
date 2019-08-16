import { Component } from '@angular/core';
import { SpotifyService } from './spotify/spotify.service';
import { FormControl } from '@angular/forms';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';

@Component({
  selector: 'app-root',
  template: `
  <h1>Hello</h1>
  <router-outlet></router-outlet>
  <ul>
      <li><a routerLink="">Home</a></li>
      <li><a routerLink="spotify">Spotify</a></li>
  </ul>
  `,
  providers: []
})
export class AppComponent {
  isLoading = true;
  searchControl = new FormControl();
  artists = [];

  constructor(private _spotifyService: SpotifyService) {
  }

  ngOnInit() {
    this.searchControl.valueChanges
      // .filter(text => text.length >= 3)
      // .debounceTime(400)
      .distinctUntilChanged()
      .subscribe(value => {
        this.isLoading = true;
        this._spotifyService.getSpotifyData(value)
          .subscribe(data => {
            this.isLoading = false;
            this.artists = data;
          });
      });
  }

}