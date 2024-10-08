 import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
 import {TokenService} from "../../services/services/token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMessage: Array<string> = [];

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private tokenService: TokenService
  ) {}

  login() {
    this.errorMessage = [];
    this.authenticationService.authenticate(
      {body: this.authRequest}).subscribe(
        {next: (result) => {
          this.tokenService.token = result.token as string;
          this.router.navigate(['books'])
          },
          error: (err) => {
          console.log(err);
          if (err.error.validationErrors)
            this.errorMessage = err.error.validationErrors;
          else
            this.errorMessage.push(err.error.errorMessage || 'An unexpected error occurred.');
          }
    });

  };

  register() {
    this.router.navigate(['register'])



  }
}
