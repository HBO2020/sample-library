import { IBook } from 'app/entities/book/book.model';

export interface IImage {
  id?: number;
  imageBookContentType?: string | null;
  imageBook?: string | null;
  imageLibelle?: string | null;
  imageCode?: number | null;
  book?: IBook | null;
}

export class Image implements IImage {
  constructor(
    public id?: number,
    public imageBookContentType?: string | null,
    public imageBook?: string | null,
    public imageLibelle?: string | null,
    public imageCode?: number | null,
    public book?: IBook | null
  ) {}
}

export function getImageIdentifier(image: IImage): number | undefined {
  return image.id;
}
