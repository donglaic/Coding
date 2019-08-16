import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'artist',
    template: `
    <h2>Artist Name: {{ name }}</h2>
    `
})
export class ArtistComponent implements OnInit, OnDestroy {
    name;
    subscription;

    constructor(private _route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this._route.params.subscribe(params => {
            this.name = params["name"];
        })
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}