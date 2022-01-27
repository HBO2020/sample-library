import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'catalogue',
        data: { pageTitle: 'sampleLibraryApp.catalogue.home.title' },
        loadChildren: () => import('./catalogue/catalogue.module').then(m => m.CatalogueModule),
      },
      {
        path: 'book',
        data: { pageTitle: 'sampleLibraryApp.book.home.title' },
        loadChildren: () => import('./book/book.module').then(m => m.BookModule),
      },
      {
        path: 'staff',
        data: { pageTitle: 'sampleLibraryApp.staff.home.title' },
        loadChildren: () => import('./staff/staff.module').then(m => m.StaffModule),
      },
      {
        path: 'member',
        data: { pageTitle: 'sampleLibraryApp.member.home.title' },
        loadChildren: () => import('./member/member.module').then(m => m.MemberModule),
      },
      {
        path: 'image',
        data: { pageTitle: 'sampleLibraryApp.image.home.title' },
        loadChildren: () => import('./image/image.module').then(m => m.ImageModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
