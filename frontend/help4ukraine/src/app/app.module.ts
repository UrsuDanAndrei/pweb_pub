import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
// import { GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule } from 'angularx-social-login';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { RoleSelectionComponent } from './role-selection/role-selection.component';
import { HubsComponent } from './hubs/hubs.component';
import { HubPageComponent } from './hub-page/hub-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    RoleSelectionComponent,
    HubsComponent,
    HubPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    // SocialLoginModule
  ],
  // providers: [
  //   {
  //     provide: 'SocialAuthServiceConfig',
  //     useValue: {
  //       autoLogin: false,
  //       providers: [
  //         {
  //           id: GoogleLoginProvider.PROVIDER_ID,
  //           provider: new GoogleLoginProvider(
  //             '20617096763-0uc2aslqge9srprtd75kca4qv4efljko.apps.googleusercontent.com'
  //           )
  //         }
  //       ]
  //     } as SocialAuthServiceConfig,
  //   } 
  // ],
  bootstrap: [AppComponent]
})
export class AppModule { }
