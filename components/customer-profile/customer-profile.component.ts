import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryType } from './../../models/coupon';
import { CustomerService } from 'src/app/services/customer.service';
import { CartService } from './../../services/cart.service';
import { Component, OnInit } from '@angular/core';
import { Coupon } from 'src/app/models/coupon';
import { Customer } from 'src/app/models/customer';
import { SecurityService } from 'src/app/services/security.service';

@Component({
  selector: 'app-customer-profile',
  templateUrl: './customer-profile.component.html',
  styleUrls: ['./customer-profile.component.css']
})
export class CustomerProfileComponent implements OnInit {

  constructor(private service: CustomerService, private security: SecurityService, private snack: MatSnackBar) { }

  coupons: Coupon[];
  customer: Customer;
  couponsByCategory: Coupon[];
  couponsByMaxPrice: Coupon[];
  prices: number[]= [100, 300, 500, 700, 1000];
  profile;
  orders;
  byCat;
  byPrice;

  type = CategoryType;

  ngOnInit(): void {
   this.service.getCustomerDetails().subscribe(
     (c)=>{this.customer = c; this.coupons = c.coupons; this.profile = true;},
     (error)=>{this.snack.open(error.error,"❌", {duration:5000});  this.security.checkErrors(error.error);}
   )
    
  }

  keys() : Array<String> {
    var keys = Object.keys(this.type);
    return keys.slice(keys.length / 2);
}


  getCouponsByCategory(category : CategoryType){
    this.couponsByCategory = this.coupons.filter(c=>c.category == category);
    console.log(this.couponsByCategory);
    this.orders = false;
    this.profile = false;
    this.byPrice = false;
    this.byCat = true;

  }

  getCouponsByMaxPrice(price: number){
    this.couponsByMaxPrice = this.coupons.filter(c=>c.price <= price);
    console.log(this.couponsByMaxPrice);
    this.byCat = false;
    this.profile = false;
    this.orders = false;
    this.byPrice = true;
  }


  showProfile(){
    this.byCat = false;
    this.byPrice = false;
    this.orders = false;
    this.profile = true;
  }
 
  showOrders(){
    this.byCat = false;
    this.byPrice = false;
    this.profile = false;
    this.orders = true;
  }

}
