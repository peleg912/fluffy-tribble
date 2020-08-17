import { OneCouponComponent } from './../one-coupon/one-coupon.component';
import { CouponImgComponent } from './../coupon-img/coupon-img.component';
import { UpdateCouponComponent } from './../update-coupon/update-coupon.component';
import { Coupon, CategoryType } from './../../models/coupon';
import { CompanyService } from 'src/app/services/company.service';
import { CompanyComponent } from './../company/company.component';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Company } from 'src/app/models/company';
import { FormControl, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { CouponsGalleryComponent } from 'src/app/coupons-gallery/coupons-gallery.component';
import { title } from 'process';
import { SecurityService } from 'src/app/services/security.service';


@Component({
  selector: 'app-all-coupons',
  templateUrl: './all-coupons.component.html',
  styleUrls: ['./all-coupons.component.css']
})
export class AllCouponsComponent implements OnInit {

  constructor(private service: CompanyService, private snack: MatSnackBar, private dialog: MatDialog, private security: SecurityService) { }

  coupons: Coupon[];
  couponsByCat: Coupon[];
  couponsByPrice: Coupon[];
  clicked;
  showByCat;
  showByPrice;
  showMainTable;
  showAllBtn;
  h2cat;
  h2prc;
  getForm: FormControl;
  prices: number[] = [100, 300, 500, 700, 1000];
  category = CategoryType;

  myControl = new FormControl();
  options: string[];
  filteredOptions: Observable<string[]>;


  ngOnInit(): void {
    this.showMainTable = true;
    this.service.getCompanyCoupons().subscribe(
      (array)=>{this.coupons = array;  this.options = this.coupons.map(c => c.title ); 
      console.log(this.options);
      this.filteredOptions = this.myControl.valueChanges
    .pipe(
      startWith(''),
      map(value => this._filter(value))
    );

    }, (error)=>{this.snack.open(error.error,"❌", {duration:5000}); this.security.checkErrors(error.error);}
      
    )
   
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }



  deleteCoupon(id: number){
    this.service.deleteCoupon(id).subscribe(
      (txt)=>{this.snack.open(txt ,"❌", {duration:3000})},
      (error)=> {this.snack.open(error.error ,"❌", {duration:3000});
      this.security.checkErrors(error.error);
    }
    )
  }

  openDialog(id: number, title: string){

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.panelClass= 'custom-updatebox';
   
    
    dialogConfig.data = {
      id: id,
      title: title,
      
  }
  this.dialog.open(UpdateCouponComponent, dialogConfig);
}


openDialog2(imageSrc: string){
  const dialogConfig = new MatDialogConfig();

  dialogConfig.disableClose = true;
  dialogConfig.autoFocus = true;
  dialogConfig.panelClass= 'custom-imgbox';
   
    
    dialogConfig.data = {
     imageSrc: imageSrc
  }

  this.dialog.open(CouponImgComponent, dialogConfig);
  
}


openDialog3(title: string){
  
    const dialogConfig = new MatDialogConfig();
  
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.panelClass= 'getone-dialogbox';
  
  
    dialogConfig.data = {
      title: this.myControl.value
      
  };
  
    this.dialog.open(OneCouponComponent, dialogConfig);
}


keys() : Array<String> {
  var keys = Object.keys(this.category);
  return keys.slice(keys.length / 2);
}


sortByCategory(category: CategoryType){
  this.showAllBtn = true;
  this.showMainTable = false;
  this.showByPrice = false;
  this.showByCat = true;
  this.couponsByCat = this.coupons.filter((c: Coupon)=> c.category == category);
}

sortByPrice(maxPrice: number){
  this.showAllBtn = true;
  this.showByCat = false;
  this.showMainTable = false;
  this.showByPrice = true;
  this.couponsByPrice = this.coupons.filter((c: Coupon)=> c.price <= maxPrice);
}

showMain(){
  this.showAllBtn = false;
  this.showByCat = false;
  this.showByPrice = false;
  this.showMainTable = true;

}



}
