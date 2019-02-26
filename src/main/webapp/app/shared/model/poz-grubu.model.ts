import { IPoz } from 'app/shared/model/poz.model';

export interface IPozGrubu {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    pozListesis?: IPoz[];
}

export class PozGrubu implements IPozGrubu {
    constructor(public id?: number, public ad?: string, public aciklama?: string, public kisaltma?: string, public pozListesis?: IPoz[]) {}
}
