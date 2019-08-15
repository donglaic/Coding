import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import { Injectable } from '@angular/core';

@Injectable()
export class SpotifyService {

    constructor(private _http: Http) {
    }

    getSpotifyData() {
        return this._http.get("http://localhost:8080/testApi")
            .map(res => res.json())
    }
}