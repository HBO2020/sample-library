import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatalogue } from '../catalogue.model';
import { CatalogueService } from '../service/catalogue.service';
import { CatalogueDeleteDialogComponent } from '../delete/catalogue-delete-dialog.component';

@Component({
  selector: 'jhi-catalogue',
  templateUrl: './catalogue.component.html',
})
export class CatalogueComponent implements OnInit {
  catalogues?: ICatalogue[];
  isLoading = false;

  constructor(protected catalogueService: CatalogueService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.catalogueService.query().subscribe({
      next: (res: HttpResponse<ICatalogue[]>) => {
        this.isLoading = false;
        this.catalogues = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICatalogue): number {
    return item.id!;
  }

  delete(catalogue: ICatalogue): void {
    const modalRef = this.modalService.open(CatalogueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catalogue = catalogue;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
