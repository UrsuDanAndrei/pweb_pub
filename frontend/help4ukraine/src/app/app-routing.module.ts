import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { HubPageComponent } from './hub-page/hub-page.component';
import { HubsComponent } from './hubs/hubs.component';
import { RoleSelectionComponent } from './role-selection/role-selection.component';

const routes: Routes = [
  { path: 'role-selection', component: RoleSelectionComponent },
  { path: '', component: HomePageComponent },
  { path: 'hubs', component: HubsComponent },
  { path: 'hub-page', component: HubPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
