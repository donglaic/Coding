import { Component } from "@angular/core";
import { SpotifyService } from "./spotify.service";

@Component({
    selector:'spotifys',
    template:`
    <h2>Spotify Results</h2>
    <div *ngFor="let spotify of spotifys">
        <a [routerLink]="['artist',spotify.name]">Link</a>
        <p>url:{{spotify.url}}</p>
        <p>name:{{spotify.name}}</p>
    </div>
    `    
})
export class SpotifyComponent{
    spotifys = [];

    constructor(private _spotifyService:SpotifyService){

    }

    ngOnInit(){
        this._spotifyService.getSpotifyData(3).subscribe(data=>this.spotifys=data);
    }
}