import { SecurityService } from './../../services/security.service';
import { OneCustomerComponent } from './../one-customer/one-customer.component';
import { UpdateCustomerComponent } from './../update-customer/update-customer.component';
import { Customer } from './../../models/customer';
import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Company } from 'src/app/models/company';
import { FormControl, Validators } from '@angular/forms';
import { UpdateCompanyComponent } from '../update-company/update-company.component';
import { Observable } from 'rxjs';
import {map, startWith} from 'rxjs/operators';

@Component({
  selector: 'app-all-customers',
  templateUrl: './all-customers.component.html',
  styleUrls: ['./all-customers.component.css']
})
export class AllCustomersComponent implements OnInit {

  constructor(private service: AdminService, private snack: MatSnackBar, private dialog: MatDialog, private security: SecurityService) { }
  
  customers : Customer[];
  clicked;
  myControl = new FormControl();
  options: string[];
  filteredOptions: Observable<string[]>;

  ngOnInit(): void {
    this.service.getAllCustomers().subscribe(
      (array)=>{this.customers = array;
        this.options = this.customers.map(c => c.firstName + " " +  c.lastName + " ," + c.email);
        this.filteredOptions = this.myControl.valueChanges
        .pipe(
          startWith(''),
          map(value => this._filter(value))
        );
      
      }, (error)=> {this.snack.open(error.error,"❌", {duration:5000});  this.security.checkErrors(error.error);}
      
    )

  }// end of ng on init

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }




  deleteCustomer(id : number){
    this.service.deleteCustomer(id).subscribe(
      (txt)=> {this.snack.open(txt ,"❌", {duration:3000})},
      (error)=> {this.snack.open(error.error ,"❌", {duration:3000});
      this.security.checkErrors(error.error);
    }
    )
  }

  openDialog(id: number) {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.panelClass= 'custom-updatebox';
    

    dialogConfig.data = {
      id: id,
    
     
      
  };

    this.dialog.open(UpdateCustomerComponent, dialogConfig);
}

openInput(){
  this.clicked = true;
}

closeInput(){
  this.clicked = false;
}

openDialog2(option: string){
let index = option.indexOf(",");
let email = option.substr(index+1, option.length)

  const dialogConfig = new MatDialogConfig();

  dialogConfig.disableClose = true;
  dialogConfig.autoFocus = true;
  dialogConfig.panelClass= 'getone-dialogbox';
  

  dialogConfig.data = {
    email: email
};

  this.dialog.open(OneCustomerComponent, dialogConfig);
}

}// end of component



