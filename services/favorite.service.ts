import { Injectable } from '@angular/core';
import { Coupon } from '../models/coupon';

@Injectable({
  providedIn: 'root'
})
export class favoriteService {

  favoriteCoupons: Coupon[] = new Array();
  counter: number=0;

  constructor() { 
    this.counter= (this.favoriteCoupons || [] ).length;
  }

  addToFavorites(coupon: Coupon){
    this.favoriteCoupons.push(coupon);
    this.counter = this.favoriteCoupons.length;
   
  }

  getFavoriteCoupons(){
    return this.favoriteCoupons || [];
  }

 
}
