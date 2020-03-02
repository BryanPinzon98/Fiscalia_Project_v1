import { Routes } from '@angular/router';

import { DashboardComponent } from '../../pages/dashboard/dashboard.component';
import { UsersComponent } from '../../pages/users/users.component';
import { RecordsComponent } from '../../pages/records/records.component';
import { UserProfileComponent } from '../../pages/user-profile/user-profile.component';
import { StatisticsComponent } from '../../pages/statistics/statistics.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard',      component: DashboardComponent },
    { path: 'user-profile',   component: UserProfileComponent },
    { path: 'statistics',     component: StatisticsComponent },
    { path: 'users',          component: UsersComponent },
    { path: 'records',        component: RecordsComponent }
];
