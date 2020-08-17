import { MatSnackBar } from '@angular/material/snack-bar';
import { CartService } from './../../services/cart.service';
import { Component, OnInit } from '@angular/core';
import { Coupon } from 'src/app/models/coupon';
import { CustomerService } from 'src/app/services/customer.service';
import { ActivatedRoute } from '@angular/router';
import { ThrowStmt } from '@angular/compiler';
import { SecurityService } from 'src/app/services/security.service';

@Component({
  selector: 'app-coupon-desgin',
  templateUrl: './coupon-desgin.component.html',
  styleUrls: ['./coupon-desgin.component.css']
})
export class CouponDesginComponent implements OnInit {

  id: number;
  coupon : Coupon;
 

  constructor(private service : CustomerService, private route: ActivatedRoute, private cart: CartService, private security: SecurityService, private snack: MatSnackBar) { }

  ngOnInit(): void {
    this.id = +this.route.snapshot.params["id"];
  
    this.service.getOneCoupon(this.id).subscribe(
      (c)=>{this.coupon = c; console.log(this.coupon)},
      (error)=>{console.log(error.error); this.security.checkErrors(error.error);}
    )
    
    
   
  }

  addToCart(){
    this.cart.addToCart(this.coupon);
    this.snack.open("Coupon added to cart!","❌", {duration:2000});
    
  }

  purchaseCoupon(c : Coupon){
    console.log(c);
    this.service.purchaseCoupon(c).subscribe(
      (coupon)=> {this.snack.open("Coupon " + coupon.title +" purchased" ,"❌", {duration:3000})},
      (error)=> {this.snack.open(error.error ,"❌", {duration:3000});
      this.security.checkErrors(error.error);
    }
    )
  }

}



