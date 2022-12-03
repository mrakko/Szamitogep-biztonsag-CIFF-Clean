import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatIconModule} from "@angular/material/icon";
import { RegisterComponent } from './components/register/register.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import { HeaderComponent } from './components/header/header.component';
import { MainLayoutComponent } from './components/main-layout/main-layout.component';
import { FileListComponent } from './components/file-list/file-list.component';
import { ProfileComponent } from './components/profile/profile.component';
import { DetailsComponent } from './components/details/details.component';
import { MatTableModule } from '@angular/material/table'  
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './services/authentication/auth.interceptor';
import { AuthGuard } from './guards/auth.guard';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HeaderComponent,
    MainLayoutComponent,
    FileListComponent,
    ProfileComponent,
    DetailsComponent
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule,
        MatToolbarModule,
        MatTableModule,
        HttpClientModule
    ],
  providers: [
    AuthGuard,
    { 
      provide: HTTP_INTERCEPTORS, 
      useClass: AuthInterceptor, 
      multi: true 
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
