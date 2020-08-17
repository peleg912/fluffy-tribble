import { CompanyService } from './../../services/company.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CategoryType, Coupon } from './../../models/coupon';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SecurityService } from 'src/app/services/security.service';

@Component({
  selector: 'app-add-coupon',
  templateUrl: './add-coupon.component.html',
  styleUrls: ['./add-coupon.component.css']
})
export class AddCouponComponent implements OnInit {

  category = CategoryType;
  addForm: FormGroup;
  coupon: Coupon;
  constructor(private builder: FormBuilder, private service: CompanyService, private snack: MatSnackBar, private security: SecurityService) { }

  ngOnInit(): void {
    this.addForm = this.builder.group({
      title:["", Validators.required],
      des:["",Validators.required],
      amount:["", [Validators.required, Validators.min(1)]], 
      price:["", [Validators.required, Validators.min(1)]], 
      sdate:["",Validators.required],
      edate:["",Validators.required],
      category:["",Validators.required],
      img:["",Validators.required],
    });
  }

  keys() : Array<String> {
    var keys = Object.keys(this.category);
    return keys.slice(keys.length / 2);
}

addCoupon(){

  if(this.addForm.invalid){
    return;
  }

  this.service.getCompanyDetails().subscribe(
    (company)=>{
      this.service.addCoupon(new Coupon(0, company, this.addForm.controls['category'].value,
      this.addForm.controls['title'].value, this.addForm.controls['des'].value, this.addForm.controls['sdate'].value,
      this.addForm.controls['edate'].value, this.addForm.controls['amount'].value, this.addForm.controls['price'].value,
      this.addForm.controls['img'].value)).subscribe(
        (coupon)=>{
          this.coupon = coupon;
          this.snack.open("The coupon " + this.addForm.controls['title'].value + " was added","❌", {duration:3000});
        },
        (error)=>{this.snack.open(error.error,"❌", {duration:3000});
        this.security.checkErrors(error.error);
      }
      )

    },
    (error)=>{this.snack.open(error.error,"❌", {duration:3000});
    this.security.checkErrors(error.error);
  }
  )
}

}
