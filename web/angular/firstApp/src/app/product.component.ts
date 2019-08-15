import { Component, Input } from "@angular/core";

@Component({
    selector: 'product',
    templateUrl:'./product.component.html',
    styles: [`
        .media{
            margin-bottom:20px;
        }
    `]
})
export class ProductComponent {
    @Input() data;
}