import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IImage } from '../image.model';
import { ImageService } from '../service/image.service';
import { ImageDeleteDialogComponent } from '../delete/image-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-image',
  templateUrl: './image.component.html',
})
export class ImageComponent implements OnInit {
  images?: IImage[];
  isLoading = false;

  constructor(protected imageService: ImageService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.imageService.query().subscribe({
      next: (res: HttpResponse<IImage[]>) => {
        this.isLoading = false;
        this.images = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IImage): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(image: IImage): void {
    const modalRef = this.modalService.open(ImageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.image = image;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
