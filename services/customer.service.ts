import { Customer } from './../models/customer';
import {  CategoryType } from './../models/coupon';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Coupon } from '../models/coupon';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private client: HttpClient) { }

  purchaseCoupon(coupon :Coupon){
    return this.client.post<Coupon>("http://localhost:8080/customer/purchaseCoupon/" + sessionStorage.token, coupon);
  }

  getCustomerCoupons(){
    return this.client.get<Coupon[]>("http://localhost:8080/customer/getCustomerCoupons/" + sessionStorage.token);
  }

  getCustomerCouponsByCategory(category: CategoryType){
    return this.client.get<Coupon[]>("http://localhost:8080/customer/getCustomerCouponsByCat/" + sessionStorage.token + "/" + category);
  }

  getCustomerCouponsByMaxPrice(maxPrice: number){
    return this.client.get<Coupon[]>("http://localhost:8080/customer/getCustomerCouponsByMax/" + sessionStorage.token + "/" + maxPrice);
  }

  getCustomerDetails(){
    return this.client.get<Customer>("http://localhost:8080/customer/getCustomerDetails/" + sessionStorage.token);
  }

  getAllCoupons(){
    return this.client.get<Coupon[]>("http://localhost:8080/customer/getAllCoupons/" + sessionStorage.token);
  }


  getOneCoupon(id: number){
    return this.client.get<Coupon>("http://localhost:8080/customer/getOneCoupon/" + sessionStorage.token + "/" +id);
  }


}
