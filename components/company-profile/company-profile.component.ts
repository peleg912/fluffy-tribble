import { CompanyService } from 'src/app/services/company.service';
import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/company';
import { SecurityService } from 'src/app/services/security.service';

@Component({
  selector: 'app-company-profile',
  templateUrl: './company-profile.component.html',
  styleUrls: ['./company-profile.component.css']
})
export class CompanyProfileComponent implements OnInit {
  
  length: number;
  company: Company;
  constructor(private service: CompanyService, private security: SecurityService) { }

  ngOnInit(): void {
    this.service.getCompanyDetails().subscribe(
      (comp)=> {this.company = comp; console.log("company " + this.company)}, (error)=>{console.log(error.error);
        this.security.checkErrors(error.error);
      })

      this.service.getCompanyCoupons().subscribe(
        (array)=>{this.length = array.length}, (error)=>{ this.security.checkErrors(error.error);}
      )
  }

}
