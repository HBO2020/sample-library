import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CatalogueService } from '../service/catalogue.service';

import { CatalogueComponent } from './catalogue.component';

describe('Catalogue Management Component', () => {
  let comp: CatalogueComponent;
  let fixture: ComponentFixture<CatalogueComponent>;
  let service: CatalogueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CatalogueComponent],
    })
      .overrideTemplate(CatalogueComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CatalogueComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CatalogueService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.catalogues?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
