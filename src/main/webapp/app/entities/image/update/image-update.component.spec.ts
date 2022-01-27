import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ImageService } from '../service/image.service';
import { IImage, Image } from '../image.model';
import { IBook } from 'app/entities/book/book.model';
import { BookService } from 'app/entities/book/service/book.service';

import { ImageUpdateComponent } from './image-update.component';

describe('Image Management Update Component', () => {
  let comp: ImageUpdateComponent;
  let fixture: ComponentFixture<ImageUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let imageService: ImageService;
  let bookService: BookService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ImageUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ImageUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ImageUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    imageService = TestBed.inject(ImageService);
    bookService = TestBed.inject(BookService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Book query and add missing value', () => {
      const image: IImage = { id: 456 };
      const book: IBook = { id: 35994 };
      image.book = book;

      const bookCollection: IBook[] = [{ id: 84493 }];
      jest.spyOn(bookService, 'query').mockReturnValue(of(new HttpResponse({ body: bookCollection })));
      const additionalBooks = [book];
      const expectedCollection: IBook[] = [...additionalBooks, ...bookCollection];
      jest.spyOn(bookService, 'addBookToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ image });
      comp.ngOnInit();

      expect(bookService.query).toHaveBeenCalled();
      expect(bookService.addBookToCollectionIfMissing).toHaveBeenCalledWith(bookCollection, ...additionalBooks);
      expect(comp.booksSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const image: IImage = { id: 456 };
      const book: IBook = { id: 50132 };
      image.book = book;

      activatedRoute.data = of({ image });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(image));
      expect(comp.booksSharedCollection).toContain(book);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Image>>();
      const image = { id: 123 };
      jest.spyOn(imageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ image });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: image }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(imageService.update).toHaveBeenCalledWith(image);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Image>>();
      const image = new Image();
      jest.spyOn(imageService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ image });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: image }));
      saveSubject.complete();

      // THEN
      expect(imageService.create).toHaveBeenCalledWith(image);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Image>>();
      const image = { id: 123 };
      jest.spyOn(imageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ image });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(imageService.update).toHaveBeenCalledWith(image);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackBookById', () => {
      it('Should return tracked Book primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBookById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
