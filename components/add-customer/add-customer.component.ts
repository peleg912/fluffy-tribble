import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from 'src/app/services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Company } from 'src/app/models/company';
import { Customer } from 'src/app/models/customer';
import { SecurityService } from 'src/app/services/security.service';

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.css']
})
export class AddCustomerComponent implements OnInit {

  constructor(private builder: FormBuilder, private service: AdminService, private snack: MatSnackBar, private security: SecurityService) { }

  addForm: FormGroup;


  ngOnInit(): void {
    this.addForm = this.builder.group({
      fname:["", [Validators.required]],
      lname:["", [Validators.required]],
      email:["",[Validators.required, Validators.email]],
      pass:["", [Validators.required, Validators.minLength(4), Validators.maxLength(10)]],  
    });
  }

  addCustomer(){
    if(this.addForm.invalid){
      return;
    }

    this.service.addCustomer(new Customer(0, this.addForm.controls['fname'].value, this.addForm.controls['lname'].value,
    this.addForm.controls['email'].value,  this.addForm.controls['pass'].value)).subscribe(
      (customer)=> {this.snack.open("Customer " + customer.firstName + " " + customer.lastName + " was added","❌", {duration:3000});
      this.addForm = this.builder.group({
        fname:[""],
        lname:[""],
        email:[""],
        pass:[""]  
      });
    } ,
      (error)=> {this.snack.open(error.error,"❌", {duration:3000});
      this.security.checkErrors(error.error);
    }
    )    
  }

}
