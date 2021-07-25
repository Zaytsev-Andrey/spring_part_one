import { Component, OnInit } from '@angular/core';
import {ProductService} from "../model/product.service";
import {Product} from "../model/product";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  public products: Product[] = [];

  public isError: boolean = false;

  constructor(public productService: ProductService) { }

  ngOnInit(): void {
    console.log(this.products);
    this.retrieveProducts();
  }

  private retrieveProducts() {
    this.productService.findAll()
      .then(res => {
        console.error(res);
        this.isError = false;
        this.products = res;
      })
      .catch(err => {
        this.isError = true;
        console.log(err);
      })
  }

  delete(id: number | null) {
    if (id != null) {
      this.productService.delete(id)
        .then(() => {
          this.retrieveProducts();
        })
    }
  }
}
