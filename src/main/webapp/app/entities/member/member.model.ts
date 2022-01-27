import { IBook } from 'app/entities/book/book.model';

export interface IMember {
  id?: number;
  idMember?: string | null;
  passwordMember?: string | null;
  book?: IBook | null;
}

export class Member implements IMember {
  constructor(public id?: number, public idMember?: string | null, public passwordMember?: string | null, public book?: IBook | null) {}
}

export function getMemberIdentifier(member: IMember): number | undefined {
  return member.id;
}
