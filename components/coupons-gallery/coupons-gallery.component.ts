
import { Component, OnInit } from '@angular/core';
import { Coupon } from 'src/app/models/coupon';
import { CustomerService } from 'src/app/services/customer.service';
import { SecurityService } from 'src/app/services/security.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Image } from 'src/app/models/image';

@Component({
  selector: 'app-coupons-gallery',
  templateUrl: './coupons-gallery.component.html',
  styleUrls: ['./coupons-gallery.component.css']
})
export class CouponsGalleryComponent implements OnInit {


  coupons: Coupon[];
  images: Image[] = new Array();

  
  constructor(private service : CustomerService, private security: SecurityService, private snack: MatSnackBar) { }

  ngOnInit(): void {

    this.service.getAllCoupons().subscribe(
      (array)=>{this.coupons = array;
        for (let c of this.coupons){
          this.images.push(new Image( c.image,  c.title, c.price, c.category, c.id))
         
        }
      },  
      (error)=>{this.snack.open(error.error ,"❌", {duration:7000});
       this.security.checkErrors(error.error);}
    )
    
  }

}
