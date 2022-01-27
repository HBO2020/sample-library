import { IBook } from 'app/entities/book/book.model';

export interface ICatalogue {
  id?: number;
  nameOfAuthor?: string | null;
  nbOfCopies?: number | null;
  books?: IBook[] | null;
}

export class Catalogue implements ICatalogue {
  constructor(public id?: number, public nameOfAuthor?: string | null, public nbOfCopies?: number | null, public books?: IBook[] | null) {}
}

export function getCatalogueIdentifier(catalogue: ICatalogue): number | undefined {
  return catalogue.id;
}
