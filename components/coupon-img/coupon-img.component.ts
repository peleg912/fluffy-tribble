import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-coupon-img',
  templateUrl: './coupon-img.component.html',
  styleUrls: ['./coupon-img.component.css']
})
export class CouponImgComponent implements OnInit {

  imgSrc: string;

  constructor(private dialogRef: MatDialogRef<CouponImgComponent>,
    @Inject(MAT_DIALOG_DATA) data) {

    this.imgSrc = data.imageSrc;
   
    
}


  ngOnInit(): void {
  }

  close(){
    this.dialogRef.close();
  
  }

}
