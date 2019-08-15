import { Component } from "@angular/core";

@Component({
    selector:'bs-jumbotron',
    template:`
        <div class="jumbotron">
            <h1><ng-content select=".heading"></ng-content></h1>
            <p><ng-content select=".body"></ng-content></p>
            <p><a class="btn btn-primary btn-lg" href="#" role="button">
                   <ng-content select=".button"></ng-content></a></p>
        </div>
    `
})
export class JumboTronComponent{

}