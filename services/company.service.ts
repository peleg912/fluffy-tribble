import { Company } from './../models/company';
import { Coupon, CategoryType } from './../models/coupon';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private client: HttpClient) { }

  addCoupon(coupon :Coupon){
    return this.client.post<Coupon>("http://localhost:8080/company/addCoupon/" + sessionStorage.token, coupon);
  }

  updateCoupon(coupon :Coupon){
    return this.client.put<Coupon>("http://localhost:8080/company/updateCoupon/" + sessionStorage.token, coupon);
  }

  deleteCoupon(id:number){
    return this.client.delete("http://localhost:8080/company/deleteCoupon/" + sessionStorage.token + "/" + id, {responseType:'text'});
  }

  getCompanyCoupons(){
    return this.client.get<Coupon[]>("http://localhost:8080/company/getCompanyCoupons/" + sessionStorage.token );
  }

  getCompanyCouponsByCategory(category:CategoryType){
    return this.client.get<Coupon[]>("http://localhost:8080/company/getCompanyCouponsByCat/" + sessionStorage.token + "/" + category );
  }

  getCompanyCouponsByMaxPrice(maxPrice:number){
    return this.client.get<Coupon[]>("http://localhost:8080/company/getCompanyCouponsByMax/" + sessionStorage.token + "/" + maxPrice );
  }

  getCompanyDetails(){
    return this.client.get<Company>("http://localhost:8080/company/getCompanyDetails/" + sessionStorage.token);
  }

  getOneCoupon(id: number){
    return this.client.get<Coupon>("http://localhost:8080/company/getOneCoupon/" + sessionStorage.token + "/" + id);
  }

  getCouponByTitle(title: string){
    return this.client.get<Coupon>("http://localhost:8080/company/getCouponByTitle/" + sessionStorage.token + "/" + title);
  }


}
