import { Customer } from './../models/customer';
import { Company } from './../models/company';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Coupon } from '../models/coupon';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private client: HttpClient) { }

  addCompany(company:Company){
    return this.client.post<Company>("http://localhost:8080/admin/addCompany/" + sessionStorage.token, company);
  }

  updateCompany(company:Company){
    return this.client.put<Company>("http://localhost:8080/admin/updateCompany/" + sessionStorage.token, company);
  }

  deleteCompany(id: number){
    return this.client.delete("http://localhost:8080/admin/deleteCompany/" + sessionStorage.token + "/" + id, {responseType:'text'} );
  }

  getAllCompanies(){
    return this.client.get<Company[]>("http://localhost:8080/admin/getAllComp/" + sessionStorage.token);
  }

  getOneCompany(id:number){
    return this.client.get<Company>("http://localhost:8080/admin/getOneComp/" + sessionStorage.token + "/" + id);
  }

  addCustomer(customer: Customer){
    return this.client.post<Customer>("http://localhost:8080/admin/addCustomer/" + sessionStorage.token, customer);
  }

  updateCustomer(customer: Customer){
    return this.client.put<Customer>("http://localhost:8080/admin/updateCustomer/" + sessionStorage.token, customer);
  }

  deleteCustomer(id: number){
    return this.client.delete("http://localhost:8080/admin/deleteCustomer/" + sessionStorage.token + "/" + id, {responseType:'text'} );
  }

  getAllCustomers(){
    return this.client.get<Customer[]>("http://localhost:8080/admin/getAllCust/" + sessionStorage.token);
  }


  getOneCustomer(id:number){
    return this.client.get<Customer>("http://localhost:8080/admin/getOneCust/" + sessionStorage.token + "/" + id);
  }

  getCouponsByCompanyId(id:number){
    return this.client.get<Coupon[]>("http://localhost:8080/admin/getCouponsByComp/" + sessionStorage.token + "/" + id);
  }

  getCompanyByName(name: string){
    return this.client.get<Company>("http://localhost:8080/admin/getCompanyByName/" + sessionStorage.token + "/" + name);
  }

  getCustomerByEmail(email: string){
    return this.client.get<Customer>("http://localhost:8080/admin/getCustomerByEmail/" + sessionStorage.token + "/" + email);
  }





}
