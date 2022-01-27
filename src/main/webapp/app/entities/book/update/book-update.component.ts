import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBook, Book } from '../book.model';
import { BookService } from '../service/book.service';

@Component({
  selector: 'jhi-book-update',
  templateUrl: './book-update.component.html',
})
export class BookUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameOFBook: [],
    authorName: [],
    nbOfBooks: [],
    isDnNomber: [],
    subjectBook: [],
    langOfBook: [],
  });

  constructor(protected bookService: BookService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ book }) => {
      this.updateForm(book);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const book = this.createFromForm();
    if (book.id !== undefined) {
      this.subscribeToSaveResponse(this.bookService.update(book));
    } else {
      this.subscribeToSaveResponse(this.bookService.create(book));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBook>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(book: IBook): void {
    this.editForm.patchValue({
      id: book.id,
      nameOFBook: book.nameOFBook,
      authorName: book.authorName,
      nbOfBooks: book.nbOfBooks,
      isDnNomber: book.isDnNomber,
      subjectBook: book.subjectBook,
      langOfBook: book.langOfBook,
    });
  }

  protected createFromForm(): IBook {
    return {
      ...new Book(),
      id: this.editForm.get(['id'])!.value,
      nameOFBook: this.editForm.get(['nameOFBook'])!.value,
      authorName: this.editForm.get(['authorName'])!.value,
      nbOfBooks: this.editForm.get(['nbOfBooks'])!.value,
      isDnNomber: this.editForm.get(['isDnNomber'])!.value,
      subjectBook: this.editForm.get(['subjectBook'])!.value,
      langOfBook: this.editForm.get(['langOfBook'])!.value,
    };
  }
}
