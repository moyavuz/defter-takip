import { IMalzeme } from 'app/shared/model/malzeme.model';

export interface IMalzemeGrubu {
    id?: number;
    ad?: string;
    aciklama?: string;
    aktifMi?: boolean;
    malzemes?: IMalzeme[];
}

export class MalzemeGrubu implements IMalzemeGrubu {
    constructor(public id?: number, public ad?: string, public aciklama?: string, public aktifMi?: boolean, public malzemes?: IMalzeme[]) {
        this.aktifMi = this.aktifMi || false;
    }
}
