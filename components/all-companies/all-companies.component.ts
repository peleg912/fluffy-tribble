import { OneCompanyComponent } from './../one-company/one-company.component';
import { UpdateCompanyComponent } from './../update-company/update-company.component';
import { AdminService } from './../../services/admin.service';
import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/company';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { FormControl, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { SecurityService } from 'src/app/services/security.service';

@Component({
  selector: 'app-all-companies',
  templateUrl: './all-companies.component.html',
  styleUrls: ['./all-companies.component.css']
})
export class AllCompaniesComponent implements OnInit {

  constructor(private service: AdminService, private snack: MatSnackBar, private dialog: MatDialog, private security: SecurityService) { }
  
  companies : Company[];
  clicked;
  getForm: FormControl;

  myControl = new FormControl();
  options: string[];
  filteredOptions: Observable<string[]>;

  ngOnInit(): void {
    this.service.getAllCompanies().subscribe(
      (array)=>{this.companies = array;
        this.options = this.companies.map(c => c.name );
        this.filteredOptions = this.myControl.valueChanges
        .pipe(
          startWith(''),
          map(value => this._filter(value))
        );
      }, (error)=> {this.snack.open(error.error,"❌", {duration:5000}); this.security.checkErrors(error.error);}
    )

   
  }// end of ng on init

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  deleteComp(id : number){
    this.service.deleteCompany(id).subscribe(
      (txt)=> {this.snack.open(txt ,"❌", {duration:3000})},
      (error)=> {this.snack.open(error.error ,"❌", {duration:3000});
      this.security.checkErrors(error.error);
    }
    )
  }

  openDialog(id: number, name: string) {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.panelClass= 'custom-updatebox';
   
    

    dialogConfig.data = {
      id: id,
      name: name,
     
      
  };

    this.dialog.open(UpdateCompanyComponent, dialogConfig);
}

openInput(){
  this.clicked = true;
}

closeInput(){
  this.clicked = false;
}

openDialog2(name: string){
  const dialogConfig = new MatDialogConfig();

  dialogConfig.disableClose = true;
  dialogConfig.autoFocus = true;
  dialogConfig.panelClass= 'getone-dialogbox';


  dialogConfig.data = {
    name: this.myControl.value
    
};

  this.dialog.open(OneCompanyComponent, dialogConfig);
}

}// end of component
