import { IStaff } from 'app/entities/staff/staff.model';
import { IImage } from 'app/entities/image/image.model';
import { IMember } from 'app/entities/member/member.model';
import { ICatalogue } from 'app/entities/catalogue/catalogue.model';

export interface IBook {
  id?: number;
  nameOFBook?: string | null;
  authorName?: string | null;
  nbOfBooks?: number | null;
  isDnNomber?: string | null;
  subjectBook?: string | null;
  langOfBook?: string | null;
  staff?: IStaff[] | null;
  images?: IImage[] | null;
  members?: IMember[] | null;
  catalogues?: ICatalogue[] | null;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public nameOFBook?: string | null,
    public authorName?: string | null,
    public nbOfBooks?: number | null,
    public isDnNomber?: string | null,
    public subjectBook?: string | null,
    public langOfBook?: string | null,
    public staff?: IStaff[] | null,
    public images?: IImage[] | null,
    public members?: IMember[] | null,
    public catalogues?: ICatalogue[] | null
  ) {}
}

export function getBookIdentifier(book: IBook): number | undefined {
  return book.id;
}
