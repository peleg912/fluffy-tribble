import { MatSnackBar } from '@angular/material/snack-bar';
import { favoriteService } from './../../services/favorite.service';
import { Component, OnInit } from '@angular/core';
import { Coupon } from 'src/app/models/coupon';
import { SecurityService } from 'src/app/services/security.service';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-favorite-coupons',
  templateUrl: './favorite-coupons.component.html',
  styleUrls: ['./favorite-coupons.component.css']
})
export class FavoriteCouponsComponent implements OnInit {

  constructor(private fav: favoriteService, private security: SecurityService, private snack: MatSnackBar, private service: CustomerService) { }

  favoriteCoupons: Coupon[];
 
  
  ngOnInit(): void {
    if(sessionStorage.type != "CU" ){
      this.snack.open("You can't be here!","❌", {duration:4000}).afterDismissed().subscribe(()=> this.security.forcedLogout());
    } 
    this.favoriteCoupons = this.fav.getFavoriteCoupons();
    console.log(this.favoriteCoupons);
   
  }

  purchaseCoupon(c: Coupon){
    this.service.purchaseCoupon(c).subscribe(
      (coupon)=> {this.snack.open("Coupon " + coupon.title +" purchased" ,"❌", {duration:3000})},
      (error)=> {this.snack.open(error.error ,"❌", {duration:3000});
      this.security.checkErrors(error.error);
    }
    )
  }

}
