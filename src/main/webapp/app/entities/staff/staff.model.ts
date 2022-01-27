import { IBook } from 'app/entities/book/book.model';

export interface IStaff {
  id?: number;
  idStaff?: string | null;
  password?: string | null;
  book?: IBook | null;
}

export class Staff implements IStaff {
  constructor(public id?: number, public idStaff?: string | null, public password?: string | null, public book?: IBook | null) {}
}

export function getStaffIdentifier(staff: IStaff): number | undefined {
  return staff.id;
}
