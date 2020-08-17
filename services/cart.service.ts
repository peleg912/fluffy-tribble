import { Injectable } from '@angular/core';
import { Coupon } from '../models/coupon';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartCoupons: Coupon[] = new Array();
  counter: number=0;
 
  constructor() { this.counter= (this.cartCoupons || [] ).length;}

  addToCart(coupon: Coupon){
    this.cartCoupons.push(coupon);
    this.counter = this.cartCoupons.length;
  }

  getCartCoupons(){
    return this.cartCoupons || [];
  }


 
}
