import { SecurityService } from './../../services/security.service';
import { AdminService } from './../../services/admin.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { CompanyService } from 'src/app/services/company.service';
import { Company } from 'src/app/models/company';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-company',
  templateUrl: './add-company.component.html',
  styleUrls: ['./add-company.component.css']
})
export class AddCompanyComponent implements OnInit {

  constructor(private builder: FormBuilder, private service: AdminService, private snack: MatSnackBar, private security: SecurityService) { }

  addForm: FormGroup;


  ngOnInit(): void {
  
    this.addForm = this.builder.group({
      name:["", [Validators.required]],
      email:["",[Validators.required, Validators.email]],
      pass:["", [Validators.required, Validators.minLength(4), Validators.maxLength(10)]],  
    });
 
  }

  addCompany(){
    if(this.addForm.invalid){
      return;
    }

    this.service.addCompany(new Company(0, this.addForm.controls['name'].value, this.addForm.controls['email'].value,
    this.addForm.controls['pass'].value )).subscribe(
      (company)=> {this.snack.open("The Comapny " + company.name + " was added","❌", {duration:3000});
      this.addForm = this.builder.group({
        name:[""],
        email:[""],
        pass:[""],  
      });
    } ,
      (error)=> {this.snack.open(error.error,"❌", {duration:5000});
      this.security.checkErrors(error.error);
    }
    )     
  }

  }


