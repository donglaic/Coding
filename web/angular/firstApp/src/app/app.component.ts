import { Component } from '@angular/core';
import { ProductService } from './product.service';

@Component({
  selector: 'app-root',
  template:`
  <products></products>
  `,
  providers:[ProductService]
})
export class AppComponent {
  title = 'firstApp';
}
